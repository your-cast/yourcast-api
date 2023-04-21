import {Module} from '@nestjs/common';
import {AuthModule} from './auth/auth.module';
import {UserModule} from './user/user.module';
import {CoreModule} from './core/core.module';
import {TypeOrmModule} from '@nestjs/typeorm';
import {CreateUsersTable1677850482182} from './migration/CreateUsersTable1677850482182';
import {SeedUserRecord1565812987671} from './migration/SeedUserRecord1677850482183';
import {UserEntity} from './user/entity/user.entity';
import {ConfigModule} from '@nestjs/config';

@Module({
    imports: [
        AuthModule,
        UserModule,
        CoreModule,
        ConfigModule.forRoot({
            isGlobal: true,
            envFilePath: ['/vault/secrets/.env.secret', '.env'],
        }),
        TypeOrmModule.forRoot({
            type: 'postgres',
            host: process.env.DB_HOST,
            port: parseInt(process.env.DB_PORT),
            username: process.env.DB_USERNAME,
            password: process.env.DB_PASSWORD,
            database: process.env.DB_NAME,
            ssl: process.env.DB_SSL == 'true',
            extra:
                process.env.DB_SSL == 'true'
                    ? {
                        ssl: {
                            rejectUnauthorized: false
                        }
                    } : undefined,
            migrations: [
                CreateUsersTable1677850482182,
                SeedUserRecord1565812987671
            ],
            entities: [UserEntity],
            migrationsRun: true
        })
    ]
})
export class AppModule {
}
