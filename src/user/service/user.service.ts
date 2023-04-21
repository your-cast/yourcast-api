import {HttpException, HttpStatus, Injectable} from '@nestjs/common';
import {InjectRepository} from '@nestjs/typeorm';
import {Repository} from 'typeorm';
import {User} from '../dto/user';
import {UserEntity} from '../entity/user.entity';
import {toUser} from '../../shared/mapper';
import {CreateUserRequest} from '../dto/user.create.request';
import {LoginUserRequest} from '../dto/user.login.create';
import {comparePasswords} from '../../shared/utils';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(UserEntity)
    private readonly userRepository: Repository<UserEntity>,
  ) {}

  async findOne(options?: object): Promise<User> {
    const user = await this.userRepository.findOne(options);
    return toUser(user);
  }

  async findByLogin({email, password}: LoginUserRequest): Promise<User> {
    const user = await this.userRepository.findOne({where: {email}});

    if (!user) {
      throw new HttpException('User not found.', HttpStatus.NOT_FOUND);
    }

    const areEqual = await comparePasswords(user.password, password);

    if (!areEqual) {
      throw new HttpException('Invalid credentials.', HttpStatus.UNAUTHORIZED);
    }

    return toUser(user);
  }

  async findByEmail({email}: any): Promise<User> {
    const user = await this.findOne({where: {email}});

    if (!user) {
      throw new HttpException('User not found.', HttpStatus.NOT_FOUND);
    }

    return user;
  }

  async create(userDto: CreateUserRequest): Promise<User> {
    const {username, password, email} = userDto;
    const userInDb = await this.userRepository.findOne({where: {username}});
    if (userInDb) {
      throw new HttpException('User already exists.', HttpStatus.BAD_REQUEST);
    }

    const user: UserEntity = await this.userRepository.create({
      username,
      password,
      email,
    });

    await this.userRepository.save(user);

    return toUser(user);
  }

  private _sanitizeUser(user: UserEntity) {
    delete user.password;
    return user;
  }
}
