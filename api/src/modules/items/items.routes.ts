import { Elysia } from "elysia";
import { itemsService } from "./items.service";
import { schemas } from "./items.schema";

export const itemsRoutes = new Elysia({ prefix: "/items" })
  .get(
    "/",
    async ({ query }) => {
      const page = Number(query.page) || 1;
      const limit = Number(query.limit) || 30;

      const search = query.search || "";
      const category = query.category || "";

      const result = await itemsService.getMany({
        page,
        limit,
        search,
        category,
      });

      return {
        items: result.data,
        total: result.total,
      };
    },
    {
      query: schemas.get.items.query,
    },
  )

  .get(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await itemsService.getOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.get.item.params,
    },
  )

  .post(
    "/",
    async ({ body, status }) => {
      const result = await itemsService.addOne(body);
      if (!result) return status("Conflict");

      return status("Created", result);
    },
    {
      body: schemas.post.item.body,
    },
  )

  .delete(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await itemsService.removeOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.delete.item.params,
    },
  );
