import { env } from "bun";
import { app } from "./app";
import { exit } from "node:process";
import { prisma } from "./database/prisma.client";

try {
  console.log(`Connecting to the database...`);
  await prisma.$connect();
  const probe = await prisma.itemInstance.count();
  console.log(`Connected to the database. Probe number: ${probe}`);
} catch (err) {
  console.error("DB failed - terminating...");
  exit(-1);
}

const PORT = Number(env.PORT);
console.log(`Starting API server...`);
app.listen(PORT, () => {
  console.log(`Server is listening on port ${PORT}`);
});
