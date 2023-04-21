FROM node:18.14.2-alpine

WORKDIR /app

COPY package*.json ./

RUN npm config set strict-ssl false

RUN npm install

COPY . .

RUN npm run build

CMD [ "node", "dist/main.js" ]
