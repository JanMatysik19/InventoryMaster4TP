import { PrismaClient } from "@/generated/prisma/client";
import { PrismaMariaDb } from "@prisma/adapter-mariadb";
import { env } from "prisma/config";

const adapter = new PrismaMariaDb({
  host: env("DATABASE_HOST"),
  port: Number(env("DATABASE_PORT")),
  user: env("DATABASE_USER"),
  password: env("DATABASE_PASSWORD"),
  database: env("DATABASE_NAME"),
  connectionLimit: 5,
});
export const prisma = new PrismaClient({ adapter });
