import { Elysia } from "elysia";
import { itemInstancesService } from "./itemInstance.service";
import { schemas } from "./itemInstance.schema";

export const itemInstancesRoutes = new Elysia({ prefix: "/item-instances" })
  .get(
    "/",
    async ({ query }) => {
      const page = Number(query.page) || 1;
      const limit = Number(query.limit) || 30;

      const itemId = query.itemId;
      const boxId = query.boxId;

      const result = await itemInstancesService.getMany({
        page,
        limit,
        itemId,
        boxId,
      });

      return {
        items: result.data,
        total: result.total,
      };
    },
    {
      query: schemas.get.itemInstances.query,
    },
  )

  .get(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await itemInstancesService.getOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.get.itemInstance.params,
    },
  )

  .post(
    "/",
    async ({ body, status }) => {
      const result = await itemInstancesService.addOne(body);
      if (!result) return status("Conflict");

      return status("Created", result);
    },
    {
      body: schemas.post.itemInstance.body,
    },
  )

  .delete(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await itemInstancesService.removeOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.delete.itemInstance.params,
    },
  )

  .patch(
    "/:id",
    async ({ params, body, status }) => {
      const id = params.id;
      const boxId = body.boxId;

      const result = await itemInstancesService.moveOne({ id, boxId });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.patch.itemInstance.params,
      body: schemas.patch.itemInstance.body,
    },
  );
