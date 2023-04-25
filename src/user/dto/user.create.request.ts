import {IsNotEmpty, IsEmail} from 'class-validator';

export class CreateUserRequest {
  @IsNotEmpty()
  name: string;

  @IsNotEmpty()
  password: string;

  @IsNotEmpty()
  @IsEmail()
  email: string;
}
