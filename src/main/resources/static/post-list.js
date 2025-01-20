const btn_search = document.getElementById("btn_search");
const search_kw = document.getElementById("search_kw");

function performSearch() {
  document.getElementById("kw").value = search_kw.value;
  document.getElementById("size").value = document.getElementById("list_size").value;
  document.getElementById("page").value = 0; // 검색 시 0 페이지부터 조회
  document.getElementById("searchForm").submit();
}

btn_search.addEventListener("click", performSearch);
search_kw.addEventListener("keydown", (event) => {
  if (event.key === "Enter") performSearch();
});
