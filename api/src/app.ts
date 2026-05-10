import Elysia from "elysia";
import { itemsRoutes } from "@/modules/items/items.routes";
import { categoriesRoutes } from "./modules/categories/categories.routes";
import { itemInstancesRoutes } from "./modules/itemInstances/itemInstance.routes";
import { boxesRoutes } from "./modules/boxes/boxes.routes";

export const app = new Elysia()
  .use(itemsRoutes)
  .use(categoriesRoutes)
  .use(itemInstancesRoutes)
  .use(boxesRoutes);
