import {IsEmail, IsNotEmpty} from 'class-validator';

export class User {
  @IsNotEmpty()
  id: string;

  @IsNotEmpty()
  username: string;

  @IsNotEmpty()
  @IsEmail()
  email: string;

  createdAt?: Date;
}
