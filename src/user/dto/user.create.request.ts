import { IsNotEmpty, IsEmail } from 'class-validator';

export class CreateUserRequest {
  @IsNotEmpty()
  username: string;

  @IsNotEmpty()
  password: string;

  @IsNotEmpty()
  @IsEmail()
  email: string;
}
