import {Module} from '@nestjs/common';
import {AuthService} from './service/auth.service';
import {PassportModule} from '@nestjs/passport';
import {JwtStrategy} from './jwt.strategy';
import {AuthController} from './http/auth.controller';
import {UserModule} from '../user/user.module';
import {JwtModule} from '@nestjs/jwt';

@Module({
    imports: [
        UserModule,
        PassportModule.register({
            defaultStrategy: 'jwt',
            property: 'user',
            session: false
        }),
        JwtModule.register({
            secret: process.env.SECRETKEY,
            signOptions: {
                expiresIn: process.env.EXPIRESIN,
            },
        }),
    ],
    controllers: [AuthController],
    providers: [AuthService, JwtStrategy],
    exports: [PassportModule]
})
export class AuthModule {
}
