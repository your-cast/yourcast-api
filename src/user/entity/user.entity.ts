import {BeforeInsert, Column, CreateDateColumn, Entity, PrimaryGeneratedColumn} from 'typeorm';
import * as bcrypt from 'bcrypt';
import {ApiProperty} from '@nestjs/swagger';

@Entity('users')
export class UserEntity {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @ApiProperty({example: 'Jonh Doe', description: 'The name of the user.'})
  @Column({type: 'varchar', nullable: false, unique: true, name: 'username'})
  username: string;

  @ApiProperty({example: '********', description: 'The password of the user.'})
  @Column({type: 'varchar', nullable: false, name: 'password'})
  password: string;

  @ApiProperty({example: 'jonh.doe@', description: 'The password of the user.'})
  @Column({type: 'varchar', nullable: false, name: 'email'})
  email: string;

  @CreateDateColumn({name: 'created_at'})
  createdAt?: Date;

  @CreateDateColumn({name: 'updated_at'})
  updatedAt?: Date;

  @BeforeInsert()
  async hashPassword() {
    this.password = await bcrypt.hash(this.password, 10);
  }
}
