import {UserEntity} from '../user/entity/user.entity';
import {User} from '../user/dto/user';

export const toUser = (data: UserEntity): User => {
  const {id, username, email, createdAt} = data;

  return {
    id,
    username,
    email,
    createdAt
  };
};
