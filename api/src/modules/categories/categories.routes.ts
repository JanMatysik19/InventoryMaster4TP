import { Elysia } from "elysia";
import { categoriesService } from "./categories.service";
import { schemas } from "./categories.schema";

export const categoriesRoutes = new Elysia({ prefix: "/categories" })
  .get(
    "/",
    async ({ query }) => {
      const page = Number(query.page) || 1;
      const limit = Number(query.limit) || 30;

      const result = await categoriesService.getMany({
        page,
        limit,
      });

      return {
        items: result.data,
        total: result.total,
      };
    },
    {
      query: schemas.get.categories.query,
    },
  )

  .get(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await categoriesService.getOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.get.category.params,
    },
  )

  .post(
    "/",
    async ({ body, status }) => {
      const result = await categoriesService.addOne(body);
      if (!result) return status("Conflict");

      return status("Created", result);
    },
    {
      body: schemas.post.category.body,
    },
  )

  .delete(
    "/:id",
    async ({ params, status }) => {
      const id = params.id;

      const result = await categoriesService.removeOne({ id });
      if (!result) return status("Not Found");

      return result;
    },
    {
      params: schemas.delete.category.params,
    },
  );
