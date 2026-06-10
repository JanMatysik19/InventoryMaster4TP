import { Elysia } from "elysia";
import { boxesService } from "./boxes.service";
import { schemas } from "./boxes.schema";

export const boxesRoutes = new Elysia({ prefix: "/boxes" })
  .get(
    "/",
    async ({ query }) => {
      const page = Number(query.page);
      const limit = Number(query.limit);
      const sequenceNumber = Number(query.sequenceNumber) || undefined;

      const result = await boxesService.getMany({
        page,
        limit,
        sequenceNumber
      });

      return {
        items: result.data,
        total: result.total,
      };
    },
    {
      query: schemas.get.boxes.query,
    },
  )

  .get(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await boxesService.getOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.get.box.params,
    },
  )

  .post(
    "/",
    async ({ status }) => {
      const result = await boxesService.addOne();
      if (!result) return status("Conflict");

      return status("Created", result);
    },
  )

  .delete(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await boxesService.removeOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.delete.box.params,
    },
  );
