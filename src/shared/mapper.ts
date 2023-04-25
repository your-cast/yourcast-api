import {UserEntity} from '../user/entity/user.entity';
import {User} from '../user/dto/user';

export const toUser = (data: UserEntity): User => {
  const {id, name, email, createdAt} = data;

  return {
    id,
    name,
    email,
    createdAt,
  };
};
