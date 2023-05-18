package com.looment.authservice.services;

import com.googlecode.jmapper.JMapper;
import com.looment.authservice.dtos.requests.*;
import com.looment.authservice.dtos.responses.TokenResponse;
import com.looment.authservice.dtos.responses.UserInfoResponse;
import com.looment.authservice.dtos.responses.UserResponse;
import com.looment.authservice.entities.UserSecurity;
import com.looment.authservice.entities.Users;
import com.looment.authservice.entities.UsersInfo;
import com.looment.authservice.exceptions.*;
import com.looment.authservice.repositories.UserInfoRepository;
import com.looment.authservice.repositories.UserRepository;
import com.looment.authservice.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtEncoder jwtEncoder;
    private final KafkaTemplate<String, Object> template;

//    private final ClassMapper mapper = Mappers.getMapper(ClassMapper.class);

//    JMapper<Users, UserRegister> userMapper = new JMapper<>(Users.class, UserRegister.class);
//    JMapper<UserResponse, Users> responseMapper = new JMapper<>(UserResponse.class, Users.class);
//    JMapper<UserInfoResponse, Users> infoResponseMapper = new JMapper<>(UserInfoResponse.class, Users.class);

    private void uniqueUser(UserRegister userRegister) {
        Optional<Users> usersEmail = userRepository.findByEmailEqualsIgnoreCaseAndDeletedAtIsNull(userRegister.getEmail());
        if (usersEmail.isPresent()) {
            throw new UserEmailExists();
        }

        Optional<Users> usersUsername = userRepository.findByUsernameEqualsIgnoreCaseAndDeletedAtIsNull(userRegister.getUsername());
        if (usersUsername.isPresent()) {
            throw new UserUsernameExists();
        }
    }

    private TokenResponse generateToken(Users users) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(users.getId().toString())
                .claim("authorities", "USER")
                .build();
        return new TokenResponse(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }

    @Override
    public void validateUser(Authentication authentication) {
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        Users users = userSecurity.getUser();
        if (users.getDeletedAt() != null) {
            throw new UserInactive();
        }
        if (users.getLockedAt() != null) {
            if (users.getLockedAt().plusMinutes(15).isAfter(LocalDateTime.now())) {
                throw new UserLocked();
            }
        }

        Random random = new Random();
        Integer otp = random.nextInt(900000) + 100000;

        users.setOtp(otp);
        users.setOtpExpired(LocalDateTime.now().plusMinutes(10));
        users.setAttemp(0);

        OTPEmail otpEmail = OTPEmail.builder()
                .username(users.getUsername())
                .email(users.getEmail())
                .otp(otp.toString())
                .build();

        userRepository.save(users);
        template.send("user-otp", otpEmail);
    }

    @Override
    public UserResponse register(UserRegister userRegister) {
        uniqueUser(userRegister);

        LocalDate dob = userRegister.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(dob, LocalDate.now());

        if (period.getYears() < 18) {
            throw new UserUnderage();
        }
        if (!UsernameValidator.isValid(userRegister.getUsername())) {
            throw new UserUsernameInvalid();
        }
        if (!EmailValidator.isValid(userRegister.getEmail())) {
            throw new UserEmailInvalid();
        }
        if (!PasswordValidator.isValid(userRegister.getPassword())) {
            throw new UserPasswordInvalid();
        }
        if (AlphabeticalValidator.isValid(userRegister.getFullname())) {
            throw new UserFullnameInvalid();
        }


        Users newUsers = modelMapper.map(userRegister, Users.class);
//        Users newUsers = user.getDestination(userRegister);
//        Users newUsers = mapper.toUsers(userRegister);

        newUsers.setPassword(passwordEncoder.encode(userRegister.getPassword()));

        UsersInfo usersInfo = new UsersInfo();
        usersInfo.setUsers(newUsers);

        userRepository.save(newUsers);
        userInfoRepository.save(usersInfo);

        return modelMapper.map(newUsers, UserResponse.class);
    }

    @Override
    public UserResponse info(UUID userId) {
        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(() -> new UserNotExists());

        return modelMapper.map(users, UserResponse.class);
    }

    @Override
        public TokenResponse verifyOtp(Authentication authentication, Integer otp) {
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        Users users = userSecurity.getUser();

        if (!users.getOtp().equals(otp)) {
            throw new UserOtpInvalid();
        }
        if (users.getOtpExpired().isBefore(LocalDateTime.now())) {
            throw new UserOtpExpired();
        }

        return generateToken(users);
    }

    @Override
    public void badCredentials(String username) {
        Users users = userRepository.findByUsernameEqualsIgnoreCaseAndDeletedAtIsNull(username)
                .orElseThrow(() -> new UserNotExists());

        if (users.getAttemp() == 3) {
            users.setLockedAt(LocalDateTime.now());
            throw new UserLocked();
        } else {
            users.setAttemp(users.getAttemp() + 1);
        }

        userRepository.save(users);
    }

    @Override
    public void resetPassword(UUID userId) {
        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(() -> new UserNotExists());

        String randomPass = RandomPassword.generate();

        users.setPassword(passwordEncoder.encode(randomPass));
        userRepository.save(users);
//        template.send("user-reset-password", randomPass);
    }

    @Override
    public void changePassword(UUID userId, PasswordRequest passwordRequest) {
        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(() -> new UserNotExists());

        users.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userRepository.save(users);
    }
}
