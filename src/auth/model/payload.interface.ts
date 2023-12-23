export interface JwtPayload {
  name: string;
  email?: string;
  issuer: string;
}
