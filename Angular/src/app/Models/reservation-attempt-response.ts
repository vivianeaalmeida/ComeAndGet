import { AdvertisementSummary } from "./advertisement-summary";

export interface ReservationAttemptResponse {
  id?: string;
  clientId?: string;
  date?: string;
  advertisementId: string;
  advertisement:AdvertisementSummary;
  status: string;
}
