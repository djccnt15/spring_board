import {
  CaseType,
  handleFormSubmit,
} from "./form-handler.js";

const categoryUpdateForm = document.getElementById("subCategoryUpdateForm")
categoryUpdateForm.addEventListener("submit", handleFormSubmit)
categoryUpdateForm.setAttribute("data-case-type", CaseType.UPDATE)
