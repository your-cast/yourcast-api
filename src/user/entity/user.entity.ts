import {BeforeInsert, Column, CreateDateColumn, Entity, PrimaryGeneratedColumn} from 'typeorm';
import * as bcrypt from 'bcrypt';

@Entity('users')
export class UserEntity {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({type: 'varchar', nullable: false, unique: true, name: "username"})
  username: string;

  @Column({type: 'varchar', nullable: false, name: "password"})
  password: string;

  @Column({type: 'varchar', nullable: false, name: "email"})
  email: string;

  @CreateDateColumn({name: "created_at"})
  createdAt?: Date;

  @CreateDateColumn({name: "updated_at"})
  updatedAt?: Date;

  @BeforeInsert()
  async hashPassword() {
    this.password = await bcrypt.hash(this.password, 10);
  }
}
