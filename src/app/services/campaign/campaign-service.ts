import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CampaignModel } from '../../model/campaign-model';

@Injectable({
  providedIn: 'root',
})
export class CampaignService {

  private readonly BASE_URL = "http://localhost:3000";

  private readonly httpClient: HttpClient;

  public constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  public get headers() {
    return {
      "Content-Type": "application/json"
    }
  }

  public getCampaignById(id: string): Observable<CampaignModel> {
    return this.httpClient.get<CampaignModel>(`${this.BASE_URL}/campaign/${id}`);
  }

  public getAllCampaigns(): Observable<CampaignModel[]> {
  return this.httpClient.get<CampaignModel[]>(`${this.BASE_URL}/campaign`);
}
  
}
