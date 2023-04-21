import {Injectable} from '@nestjs/common';
import {RegistrationResponse} from '../interfaces/regisration-response.interface';
import {LoginResponse} from '../interfaces/login-response.interface';
import {JwtPayload} from '../interfaces/payload.interface';
import {CreateUserRequest} from '../../user/dto/user.create.request';
import {UserService} from '../../user/service/user.service';
import {LoginUserRequest} from '../../user/dto/user.login.create';
import {User} from '../../user/dto/user';
import {JwtService} from '@nestjs/jwt';
import {ProfileResponse} from '../interfaces/profile-response.interface';

@Injectable()
export class AuthService {
    constructor(
        private readonly userService: UserService,
        private readonly jwtService: JwtService
    ) {
    }

    async register(createUserRequest: CreateUserRequest): Promise<RegistrationResponse> {
        let status: RegistrationResponse = {
            success: true,
            message: 'user registered'
        };

        try {
            await this.userService.create(createUserRequest);
        } catch (err) {
            status = {
                success: false,
                message: err
            };
        }

        return status;
    }

    async login(loginUserRequest: LoginUserRequest): Promise<LoginResponse> {
        const user = await this.userService.findByLogin(loginUserRequest);

        const token = this.createToken(user);

        return {
            email: user.email,
            username: user.username,
            ...token
        };
    }

    async profile(request: any): Promise<ProfileResponse> {
        const user = await this.userService.findByEmail(request.user);

        return {
            username: user.username,
            email: user.email,
            createdAt: user.createdAt
        }
    }

    private createToken({username, email}: User): any {
        const expiresIn = process.env.EXPIRESIN;
        const payload: JwtPayload = {username: username, email: email, issuer: 'your-cast.com'};
        const accessToken = this.jwtService.sign(payload);
        return {
            expiresIn,
            accessToken
        };
    }
}
