import {Module} from '@nestjs/common';
import {UserEntity} from './entity/user.entity';
import {TypeOrmModule} from '@nestjs/typeorm';
import {UserService} from './service/user.service';

@Module({
  imports: [TypeOrmModule.forFeature([UserEntity])],
  controllers: [],
  providers: [UserService],
  exports: [UserService],
})
export class UserModule {}
