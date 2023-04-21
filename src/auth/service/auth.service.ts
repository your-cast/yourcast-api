import {HttpException, HttpStatus, Injectable} from '@nestjs/common';
import {RegistrationStatus} from '../interfaces/regisration-status.interface';
import {LoginStatus} from '../interfaces/login-status.interface';
import {JwtPayload} from '../interfaces/payload.interface';
import {CreateUserDto} from '../../user/dto/user.create.dto';
import {UserService} from '../../user/service/user.service';
import {LoginUserDto} from '../../user/dto/user-login.dto';
import {UserDto} from '../../user/dto/user.dto';
import {JwtService} from '@nestjs/jwt';

@Injectable()
export class AuthService {
    constructor(
        private readonly userService: UserService,
        private readonly jwtService: JwtService
    ) {
    }

    async register(userDto: CreateUserDto): Promise<RegistrationStatus> {
        let status: RegistrationStatus = {
            success: true,
            message: 'user registered'
        };

        try {
            await this.userService.create(userDto);
        } catch (err) {
            status = {
                success: false,
                message: err
            };
        }

        return status;
    }

    async login(loginUserDto: LoginUserDto): Promise<LoginStatus> {
        const user = await this.userService.findByLogin(loginUserDto);

        const token = this.createToken(user);

        return {
            username: user.username,
            ...token
        };
    }

    async validateUser(payload: JwtPayload): Promise<UserDto> {
        const user = await this.userService.findByPayload(payload);
        if (!user) {
            throw new HttpException('Invalid token', HttpStatus.UNAUTHORIZED);
        }
        return user;
    }

    private createToken({username}: UserDto): any {
        const expiresIn = process.env.EXPIRESIN;
        const user: JwtPayload = {username: username, issuer: 'sign@your-cast.com', audience: 'your-cast.com'};
        const accessToken = this.jwtService.sign(user);
        return {
            expiresIn,
            accessToken
        };
    }
}
