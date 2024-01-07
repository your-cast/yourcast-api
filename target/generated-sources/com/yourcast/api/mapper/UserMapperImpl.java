package com.yourcast.api.mapper;

import com.yourcast.api.hibernate.entity.UserEntity;
import com.yourcast.api.http.model.request.SignUpRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-07T23:17:42+0200",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity mapCreateUser(SignUpRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setPassword( encodePassword( request ) );
        userEntity.setName( request.getName() );
        userEntity.setEmail( request.getEmail() );

        userEntity.setSystemId( generateSystemId() );

        return userEntity;
    }
}
