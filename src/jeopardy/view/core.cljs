(ns jeopardy.view.core)

(defn receive-get-board
  [db response]
  (assoc db :game response)
  )
