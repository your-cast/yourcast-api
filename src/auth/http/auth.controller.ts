import {Body, Controller, Get, HttpException, HttpStatus, Logger, Post, Req, UseGuards} from '@nestjs/common';
import {CreateUserRequest} from '../../user/dto/user.create.request';
import {RegistrationResponse} from '../interfaces/regisration-response.interface';
import {AuthService} from '../service/auth.service';
import {LoginResponse} from '../interfaces/login-response.interface';
import {LoginUserRequest} from '../../user/dto/user.login.create';
import {AuthGuard} from '../guard/auth.guard';
import {ProfileResponse} from '../interfaces/profile-response.interface';
import {ApiBearerAuth, ApiOperation, ApiResponse, ApiTags} from '@nestjs/swagger';

@ApiBearerAuth()
@ApiTags('auth')
@Controller('api/v2/auth')
export class AuthController {
  private readonly logger = new Logger(AuthController.name);

  constructor(private readonly authService: AuthService) {
  }

  @ApiOperation({summary: 'Registration on the platform.'})
  @ApiResponse({
    status: 201,
    description: 'Forbidden.'
  })
  @Post('register')
  public async register(@Body() createUserRequest: CreateUserRequest): Promise<RegistrationResponse> {
    this.logger.log(
      `[AuthController.register income request for register by email: ] ${createUserRequest.email}`,
    );
    const result: RegistrationResponse = await this.authService.register(
      createUserRequest,
    );
    if (!result.success) {
      throw new HttpException(result.message, HttpStatus.BAD_REQUEST);
    }
    return result;
  }

  @ApiOperation({summary: 'Login to the platform.'})
  @ApiResponse({
    status: 200,
    description: 'The found record'
  })
  @Post('login')
  public async login(@Body() loginUserRequest: LoginUserRequest): Promise<LoginResponse> {
    this.logger.log(
      `[AuthController.login income request for login by email: ] ${loginUserRequest.email}`,
    );
    return await this.authService.login(loginUserRequest);
  }

  @ApiOperation({summary: 'Get user profile from the platform.'})
  @ApiResponse({
    status: 200,
    description: 'User detailed information.'
  })
  @Get('profile')
  @UseGuards(AuthGuard)
  public async profile(@Req() request: any): Promise<ProfileResponse> {
    this.logger.log(
      `[AuthController.profile income request for profile to user: ] ${request.user.email}`,
    );
    return await this.authService.profile(request);
  }
}
