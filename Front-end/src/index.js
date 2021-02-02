import React from "react";
import ReactDOM from "react-dom";
import Table from "react-table";
import useContactsTable from "./useContactsTable";
import "react-table/react-table.css";

const App = () => {
  const { gridState, error, refetch, invalidate } = useContactsTable();

  if (error) {
    return (
      <p>
        <span role="img" aria-label="Problem">

        </span>{" "}
        Something broke... <button onClick={refetch}>Retry</button>
      </p>
    );
  }

  const columns = [

    { Header: "Title", accessor: "title", maxWidth: 100 },
    { Header: "Price", accessor: "price" },
    {Header:"Brand",accessor:"brand"},
    { Header: "Id", accessor: "_id" }
  ];

  return (
    <>
      <div style={{ paddingTop: 10 }}>
        <Table columns={columns} {...gridState} />
      </div>
    </>
  );
};

const rootElement = document.getElementById("root");
ReactDOM.render(<App />, rootElement);
