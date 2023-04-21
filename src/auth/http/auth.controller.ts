import {Body, Controller, Get, HttpException, HttpStatus, Post, Req, UseGuards} from '@nestjs/common';
import {CreateUserRequest} from '../../user/dto/user.create.request';
import {RegistrationResponse} from '../interfaces/regisration-response.interface';
import {AuthService} from '../service/auth.service';
import {LoginResponse} from '../interfaces/login-response.interface';
import {LoginUserRequest} from '../../user/dto/user.login.create';
import {AuthGuard} from '../guard/auth.guard';
import {ProfileResponse} from '../interfaces/profile-response.interface';

@Controller('auth')
export class AuthController {
  constructor(
      private readonly authService: AuthService
  ) {
  }

  @Post('register')
  public async register(@Body() createUserRequest: CreateUserRequest): Promise<RegistrationResponse> {
    const result: RegistrationResponse = await this.authService.register(createUserRequest);
    if (!result.success) {
      throw new HttpException(result.message, HttpStatus.BAD_REQUEST);
    }
    return result;
  }

  @Post('login')
  public async login(@Body() loginUserRequest: LoginUserRequest): Promise<LoginResponse> {
    return await this.authService.login(loginUserRequest);
  }

  @Get('profile')
  @UseGuards(AuthGuard)
  public async profile(@Req() request: any): Promise<ProfileResponse> {
    return await this.authService.profile(request);
  }
}
