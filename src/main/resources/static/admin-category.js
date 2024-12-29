import {
  CaseType,
  handleFormSubmit,
} from "./form-handler.js";

const categoryCreateForm = document.getElementById("mainCategoryCreateForm")
categoryCreateForm.addEventListener("submit", handleFormSubmit)
categoryCreateForm.setAttribute("data-case-type", CaseType.CREATE)
