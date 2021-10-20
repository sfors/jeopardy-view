(ns jeopardy.view.events
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ajax.core :as ajax]))


(re-frame/reg-event-db
  ::initialize-db
  (fn [_]
    {:clicks 0}))


(re-frame/reg-event-db
  ::button-clicked
  (fn [db] (update db :clicks inc)))


(re-frame/reg-event-fx
  ::call-service
  (fn [_ _]
    {:fx [[:http-xhrio
           {:method :get
            :uri "http://localhost:7000/"
            :response-format
                    (ajax/json-response-format {:keywords? true})
            :on-success [::response-received]
            :on-failure [::service-call-failed]
            }]]}))

(re-frame/reg-event-db
  ::response-received
  (fn [db [_ response]]
    (println response)
    (assoc db :service-response response)))
