import {
  CaseType,
  handleFormSubmit,
} from "./form-handler.js";

const mainCategoryCreateForm = document.getElementById("mainCategoryCreateForm")
mainCategoryCreateForm.addEventListener("submit", handleFormSubmit)
mainCategoryCreateForm.setAttribute("data-case-type", CaseType.CREATE)

const subCategoryCreateForm = document.getElementById("subCategoryCreateForm")
subCategoryCreateForm.addEventListener("submit", handleFormSubmit)
subCategoryCreateForm.setAttribute("data-case-type", CaseType.CREATE)
