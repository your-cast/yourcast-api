import {UserEntity} from '../user/entity/user.entity';
import {UserDto} from '../user/dto/user.dto';

export const toUser = (data: UserEntity): UserDto => {
  const {id, username, email} = data;

  return {
    id,
    username,
    email
  };
};
