export interface CampaignModel {
    id: string,
    title: string,
    description: string,
    createdAt?: string,
    ownerName: string,
    donations: number
    goal: number
    raised: number
    isOwner: boolean
}

export interface CampaignRequest {
    title: string;
    goal: number;
    description: string;
}