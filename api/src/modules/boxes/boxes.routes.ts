import { Elysia } from "elysia";
import { boxesService } from "./boxes.service";
import { schemas } from "./boxes.schema";

export const boxesRoutes = new Elysia({ prefix: "/boxes" })
  .get(
    "/",
    async ({ query }) => {
      const page = Number(query.page) || 1;
      const limit = Number(query.limit) || 30;

      const shelfId = query.shelfId;

      const result = await boxesService.getMany({
        page,
        limit,
        shelfId,
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
    async ({ body, status }) => {
      const result = await boxesService.addOne(body);
      if (!result) return status("Conflict");

      return status("Created", result);
    },
    {
      body: schemas.post.box.body,
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
  )

  .patch(
    "/:id",
    async ({ params, body, status }) => {
      const id = params.id;
      const shelfId = body.shelfId;

      const result = await boxesService.moveOne({ id, shelfId });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.patch.box.params,
      body: schemas.patch.box.body,
    },
  );
