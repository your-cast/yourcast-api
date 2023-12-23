import {DataSource} from 'typeorm';

export const AppDataSource = new DataSource({
  type: 'postgres',
  host: 'postgres',
  port: 5432,
  username: 'postgres',
  password: 'secret',
  database: 'yourcast',
  synchronize: false,
  migrations: ['./src/migration/*.{ts,js}'],
});
