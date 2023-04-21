import {HttpException, HttpStatus, Injectable} from '@nestjs/common';
import {InjectRepository} from '@nestjs/typeorm';
import {Repository} from 'typeorm';
import {UserDto} from '../dto/user.dto';
import {UserEntity} from '../entity/user.entity';
import {toUser} from '../../shared/mapper';
import {CreateUserDto} from '../dto/user.create.dto';
import {LoginUserDto} from '../dto/user-login.dto';
import {comparePasswords} from '../../shared/utils';

@Injectable()
export class UserService {
  constructor(
      @InjectRepository(UserEntity)
      private readonly userRepository: Repository<UserEntity>
  ) {
  }

  async findOne(options?: object): Promise<UserDto> {
    const user = await this.userRepository.findOne(options);
    return toUser(user);
  }

  async findByLogin({username, password}: LoginUserDto): Promise<UserDto> {
    const user = await this.userRepository.findOne({where: {username}});

    if (!user) {
      throw new HttpException('User not found', HttpStatus.UNAUTHORIZED);
    }

    const areEqual = await comparePasswords(user.password, password);

    if (!areEqual) {
      throw new HttpException('Invalid credentials', HttpStatus.UNAUTHORIZED);
    }

    return toUser(user);
  }

  async findByPayload({username}: any): Promise<UserDto> {
    return await this.findOne({where: {username}});
  }

  async create(userDto: CreateUserDto): Promise<UserDto> {
    const {username, password, email} = userDto;
    const userInDb = await this.userRepository.findOne({where: {username}});
    if (userInDb) {
      throw new HttpException('User already exists', HttpStatus.BAD_REQUEST);
    }

    const user: UserEntity = await this.userRepository.create({
      username,
      password,
      email
    });

    await this.userRepository.save(user);

    return toUser(user);
  }

  private _sanitizeUser(user: UserEntity) {
    delete user.password;
    return user;
  }
}
