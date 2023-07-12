FROM node:18.14.2-alpine

WORKDIR /app

COPY package*.json ./

RUN yarn install

COPY . .

RUN yarn build

CMD [ "node", "dist/main.js" ]
