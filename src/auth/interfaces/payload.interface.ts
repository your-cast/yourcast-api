export interface JwtPayload {
  username: string;
  email?: string;
  issuer: string;
}
