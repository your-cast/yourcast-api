import {Test, TestingModule} from '@nestjs/testing';
import {INestApplication} from '@nestjs/common';
import * as request from 'supertest';
import {AppModule} from '../src/app.module';

describe('AppController (e2e)', () => {
  let app: INestApplication;

  beforeEach(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule]
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('/auth/register (POST)', () => {
    return request(app.getHttpServer())
        .post('/auth/register')
        .send({
          'username': 'Dmytro',
          'password': '123456',
          'email': 'dmytro@email.com'
        })
        .expect(201)
        .expect({
            "success": true,
            "message": "user registered"
        });
  });

  it('/auth/login (POST)', () => {
    return request(app.getHttpServer())
        .post('/auth/register')
        .send({
          'username': 'Dmytro',
          'password': '123456'
        })
        .expect(200)
        .expect({
            "username": "Dmytro",
            "expiresIn": "5h",
        });
  });
});
