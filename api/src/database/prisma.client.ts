import { PrismaMariaDb } from "@prisma/adapter-mariadb";
import { env } from "prisma/config";
import { PrismaClient } from "@prisma/client";

const adapter = new PrismaMariaDb({
  host: env("DATABASE_HOST"),
  port: Number(env("DATABASE_PORT")),
  user: env("DATABASE_USER"),
  password: env("DATABASE_PASSWORD"),
  database: env("DATABASE_NAME"),
  connectionLimit: 1,
});

const globalORM = new PrismaClient({ adapter });
export const prisma = globalORM;
