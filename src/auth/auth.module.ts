import {Module} from '@nestjs/common';
import {AuthService} from './service/auth.service';
import {PassportModule} from '@nestjs/passport';
import {AuthController} from './http/auth.controller';
import {UserModule} from '../user/user.module';
import {JwtModule} from '@nestjs/jwt';
import * as dotenv from 'dotenv';

dotenv.config();

@Module({
    controllers: [AuthController],
    providers: [AuthService],
    imports: [
        UserModule,
        PassportModule.register({
            defaultStrategy: 'jwt',
            property: 'user',
            session: false
        }),
        JwtModule.register({
            secret: process.env.SECRET_KEY,
            signOptions: {
                expiresIn: process.env.EXPIRES_IN,
            },
        }),
    ],
    exports: [PassportModule]
})
export class AuthModule {
}
