import {Injectable, Logger} from '@nestjs/common';
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
  private readonly logger = new Logger(AuthService.name);

  constructor(
    private readonly userService: UserService,
    private readonly jwtService: JwtService,
  ) {}

  async register(
    createUserRequest: CreateUserRequest,
  ): Promise<RegistrationResponse> {
    this.logger.log(
      `[AuthService.register try register email: ] ${createUserRequest.email}`,
    );

    let response: RegistrationResponse = {
      success: true,
      message: 'User successfully registered.',
    };

    try {
      await this.userService.create(createUserRequest);
    } catch (err) {
      response = {
        success: false,
        message: err,
      };
    }

    return response;
  }

  async login(loginUserRequest: LoginUserRequest): Promise<LoginResponse> {
    this.logger.log(
      `[AuthService.login try login email: ] ${loginUserRequest.email}`,
    );

    const user = await this.userService.findByLogin(loginUserRequest);

    const token = this.createToken(user);

    return {
      email: user.email,
      name: user.name,
      ...token,
    };
  }

  async profile(request: any): Promise<ProfileResponse> {
    this.logger.log(
      `[AuthService.profile try get profile by email: ] ${request.user.email}`,
    );

    const user: User = await this.userService.findByEmail(request.user);

    return {
      name: user.name,
      email: user.email,
      createdAt: user.createdAt,
    };
  }

  private createToken({name, email}: User): any {
    this.logger.log(
      `[AuthService.createToken try generate token for email: ] ${email}`,
    );

    const expiresIn: string = process.env.EXPIRESIN;
    const payload: JwtPayload = {
      name: name,
      email: email,
      issuer: 'your-cast.com',
    };
    const accessToken: string = this.jwtService.sign(payload);
    return {
      expiresIn,
      accessToken,
    };
  }
}
