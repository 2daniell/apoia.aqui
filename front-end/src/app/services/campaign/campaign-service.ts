import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CampaignModel, CampaignRequest } from '../../model/campaign-model';
import { DashboardStats } from '../../model/dashboard-model';

@Injectable({
  providedIn: 'root',
})
export class CampaignService {

  private readonly BASE_URL = "http://localhost:8080/api/campaigns";

  private readonly httpClient: HttpClient;

  public constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  public getCampaignById(id: string): Observable<CampaignModel> {
    return this.httpClient.get<CampaignModel>(`${this.BASE_URL}/${id}`);
  }

  public getDashboardStats(): Observable<DashboardStats> {
    return this.httpClient.get<DashboardStats>(`${this.BASE_URL}/stats`);
  }

  public getUserCampaigns(): Observable<CampaignModel[]> {
    return this.httpClient.get<CampaignModel[]>(`${this.BASE_URL}/my`)
  }

  public getCampaigns(page: number = 1, pageSize: number = 3): Observable<CampaignModel[]> {
    return this.httpClient.get<CampaignModel[]>(`${this.BASE_URL}/all?page=${page}&pageSize=${pageSize}`)
  }

  public deleteCampaign(campaignId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.BASE_URL}/${campaignId}`)
  }

  public createCampaign(data: CampaignRequest): Observable<CampaignModel> {
    return this.httpClient.post<CampaignModel>(`${this.BASE_URL}`, data,)
  }

  public updateCampaign(campaignId: string, data: CampaignRequest): Observable<CampaignModel> {
    return this.httpClient.put<CampaignModel>(`${this.BASE_URL}/${campaignId}`, data,)
  }
  
}
