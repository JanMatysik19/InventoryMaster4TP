import Elysia from "elysia";
import { itemsRoutes } from "@/modules/items/items.routes";
import { itemInstancesRoutes } from "./modules/itemInstances/itemInstance.routes";
import { boxesRoutes } from "./modules/boxes/boxes.routes";
import { inventoryRoutes } from "./modules/inventory/inventory.routes";

export const app = new Elysia()
  .use(boxesRoutes)
  .use(itemsRoutes)
  .use(itemInstancesRoutes)
  .use(inventoryRoutes)
