import React from "react";
import _ from "lodash";
import useApi from "./useApi";

export default function useContactsTable() {
  // Though this is a GET, use a POST & method override because the sorted and
  // filtered params are complex (arrays of objects) for easier parsing.
  const url =
    "http://localhost:8080/allProducts";
  const [gridState, setGridState] = React.useState();
  const { results, loading, error, refetch, invalidate } = useApi(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(gridState)
  });

  // Debounce state changes to reduce the spam when filtering
  const onFetchData = React.useCallback(
    _.debounce((state) => {
      const { page, pageSize, sorted, filtered } = state;
      setGridState({ page: page + 1, per: pageSize, sorted, filtered });
    }, 80)
  );

  return {
    gridState: {
      defaultPageSize: 10,
      data: _.get(results, "content", []),
      pages: _.get(results, "totalPages", 0),
      filterable: true,
      manual: true,
      onFetchData,
      loading
    },
    error,
    refetch,
    invalidate
  };
}
