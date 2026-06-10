import { Elysia } from "elysia";
import { itemsService } from "./items.service";
import { schemas } from "./items.schema";

export const itemsRoutes = new Elysia({ prefix: "/items" })
  .get(
    "/",
    async ({ query }) => {
      const page = Number(query.page);
      const limit = Number(query.limit);
      const search = query.search || "";

      const result = await itemsService.getMany({
        page,
        limit,
        search,
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
  )
  
  .put(
    "/:id",
    async ({ params, body, status }) => {
      const id = params.id;

      const result = await itemsService.updateOne({ id, ...body });
      if(!result) return status("Conflict");

      return result;
    },
    {
      params: schemas.put.item.params,
      body: schemas.put.item.body,
    },
  );
