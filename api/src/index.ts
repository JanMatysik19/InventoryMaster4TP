import { env } from "bun";
import { app } from "./app";

const PORT = Number(env.PORT);
app.listen(PORT, () => {
  console.log(`Server is listening on port ${PORT}`);
});
