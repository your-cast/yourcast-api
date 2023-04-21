import {IsNotEmpty} from 'class-validator';

export class LoginUserRequest {
  @IsNotEmpty()
  readonly email: string;

  @IsNotEmpty()
  readonly password: string;
}
