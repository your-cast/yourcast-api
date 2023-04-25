import {BeforeInsert, Column, CreateDateColumn, Entity, PrimaryGeneratedColumn} from 'typeorm';
import * as bcrypt from 'bcrypt';
import {ApiProperty} from '@nestjs/swagger';
import {makeSystemId} from '../../shared/utils';

@Entity('users')
export class UserEntity {
  @PrimaryGeneratedColumn('increment')
  id: string;

  @ApiProperty({example: 'Jonh Doe', description: 'The name of the user.'})
  @Column({type: 'varchar', nullable: false, name: 'name'})
  name: string;

  @ApiProperty({example: '********', description: 'The password of the user.'})
  @Column({type: 'varchar', nullable: false, name: 'password'})
  password: string;

  @ApiProperty({example: 'jonh.doe@example.com', description: 'The email of the user.'})
  @Column({type: 'varchar', nullable: false, unique: true, name: 'email'})
  email: string;

  @Column({type: 'varchar', nullable: false, unique: true, name: 'system_id'})
  systemId: string;

  @Column({name: 'email_verified_at', nullable: true})
  email_verified_at?: Date;

  @CreateDateColumn({name: 'created_at'})
  createdAt?: Date;

  @CreateDateColumn({name: 'updated_at'})
  updatedAt?: Date;

  @BeforeInsert()
  async hashPassword() {
    this.password = await bcrypt.hash(this.password, 10);
    this.systemId = await makeSystemId(4);
  }
}
