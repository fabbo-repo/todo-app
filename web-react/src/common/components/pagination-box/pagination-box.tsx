import React from "react";
import "./pagination-box.css";

interface PaginationBoxProps {
  itemsPerPage: number;
  totalItems: number;
  onNewPage: (pageNumber: number) => void;
  currentPage: number;
}

const PaginationBox: React.FC<PaginationBoxProps> = ({
  itemsPerPage,
  totalItems,
  onNewPage,
  currentPage,
}) => {
  const pageNumbers = [];

  for (let i = 0; i < Math.ceil(totalItems / itemsPerPage); i++) {
    pageNumbers.push(i + 1);
  }

  return (
    <nav className="pagination-box">
      <ul>
        {pageNumbers.map((number) => (
          <li key={number}>
            <button
              onClick={() => onNewPage(number)}
              className={currentPage + 1 === number ? "active" : ""}
            >
              {number}
            </button>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default PaginationBox;
